package ru.ssau.controllers;

import model.Auxiliary.ReaderRawData;
import model.Calculation.ConsiderStatistics;
import model.Calculation.Converter;
import model.Data.Data;
import model.Data.RawData;
import model.Data.StatisticsData;
import model.Exception.FileFormatException;
import model.Exception.LittleStatisticalDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import tools.FileUtils;
import tools.StreamHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private ArrayList<RawData> rawData;
    private List<String> fileNames;
    private int minBorder;
    private int maxBorder;
    private Data data;
    private double[] heights;

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public ResponseEntity   calculateStatistics() {
        StatisticsData statisticsData = null;
        if (rawData != null && !rawData.isEmpty()) {
            ConsiderStatistics considerStatistics = new ConsiderStatistics();
            try {
                statisticsData = considerStatistics.createStatisticsData(rawData);
            } catch (LittleStatisticalDataException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(statisticsData, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public  ResponseEntity save(@RequestParam(value = "file") List<CommonsMultipartFile> files) throws IOException {
        fileNames = new ArrayList<String>();
        rawData = new ArrayList<RawData>(); //тут данные уже прошедшие через парсер
        if(null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
                InputStream is = multipartFile.getInputStream();
                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                RawData readDataElem = null;
                try {
                    readDataElem = ReaderRawData.readData(fileName,is);
                } catch (FileFormatException e) {
                    e.printStackTrace();
                }
                rawData.add(readDataElem);
            }
        }
        return new ResponseEntity(fileNames,HttpStatus.OK);
    }

    @RequestMapping(value = "/Help", method = RequestMethod.GET)
    public ResponseEntity  showHelpPage(){
        InputStream inputStream=null;
        RawData readDataElem = null;
        rawData = new ArrayList<RawData>(); //тут данные уже прошедшие через парсер
        fileNames = new ArrayList<String>();
        String s= System.getProperty("user.dir");
        String path = s+"\\datasets";
        File folder = new File(path);
        for (final File fileEntry : folder.listFiles()) {
            try {
                inputStream = new FileInputStream (fileEntry);
                fileNames.add(fileEntry.getName());
                readDataElem = ReaderRawData.readData(fileEntry.getName(),inputStream);
                rawData.add(readDataElem);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (FileFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity(fileNames,HttpStatus.OK);
    }

    @RequestMapping(value = "/calculateProfile", method = RequestMethod.POST)
    public ResponseEntity  calculateProfileFromFile(@RequestParam(value = RawData.FILENAME) String fileName){
        if (fileNames!=null||rawData.size()>0){
            List<RawData> data = StreamHelper.getRawDataByFileName(fileName,rawData);
            return new ResponseEntity(data.get(0),HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);//todo stream api bug

    }

    @RequestMapping(value = "/calculateCluglogramme", method = RequestMethod.POST)
    public ResponseEntity calculateCruglogramme(@RequestParam String fileName,
                                                @RequestParam String mode){
        boolean type;
        RawData elem = StreamHelper.getRawDataByFileName(fileName,rawData).get(0);
        if (mode.equals("grann")){// гранность
            type = false;
            minBorder=16;
            maxBorder=150;
        }
        else {
            type=true;// волнистость
            minBorder=0;
            maxBorder=15;
        }
        Converter  converter =new Converter(elem,type);
        data = converter.createData(type,minBorder,maxBorder,1);//1- номер сечения. Спросить откуда брать сечение
        heights=data.getH();
        return new  ResponseEntity(heights,HttpStatus.OK);
    }

    @RequestMapping(value = "/uploadAmplitudes", method = RequestMethod.GET)
    public ResponseEntity writeAmplitudesToFile() throws IOException {
            int i = 1;
            StatisticsData statisticsData=null;
            List<String> amplitudes = new LinkedList<>();
            for (double amplitude:statisticsData.getAmplitudes()){
                amplitudes.add(String.valueOf(i).concat("  ").concat(String.valueOf(amplitude)));
                i++;
            }
            FileUtils.writeFile("amplitudes.txt",amplitudes);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/uploadCruglogramme", method = RequestMethod.GET)
    public ResponseEntity writeProfile() throws IOException {
        if (heights!=null){
            int i = 1;
            List<String> outH = new LinkedList<>();
            for (double h:heights){
                outH.add(String.valueOf(i).concat("  ").concat(String.valueOf(h)));
                i++;
            }
            FileUtils.writeFile("cruglogramme.txt",outH);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
