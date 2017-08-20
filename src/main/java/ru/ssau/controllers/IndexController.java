package ru.ssau.controllers;

import model.Auxiliary.ReaderRawData;
import model.Calculation.ConsiderStatistics;
import model.Calculation.Converter;
import model.Data.Data;
import model.Data.RawData;
import model.Data.StatisticsData;
import model.Exception.FileFormatException;
import model.Exception.LittleStatisticalDataException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tools.StreamHelper;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private ArrayList<RawData> rawData;
    private  static List<String> fileNames;
    private int minBorder;
    private int maxBorder;
    private boolean mode;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String slash(Model model, HttpSession session) {
        return "redirect:/welcome";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/welcome")
    public ModelAndView welcome(Model model, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("welcome");
        logger.info("logger work!!!");
        return modelAndView;
    }

    @RequestMapping(value = "/Statistics", method = RequestMethod.GET)
    public ModelAndView showStat(){
        ModelAndView modelAndView = new ModelAndView("statistics");
        return modelAndView;
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public ResponseEntity<String> calculateStatistics(){
        String jsonStr = "";
        if(rawData!=null&&!rawData.isEmpty()){
            ConsiderStatistics considerStatistics = new ConsiderStatistics();
            StatisticsData statisticsData = null;
            try {
                statisticsData = considerStatistics.createStatisticsData(rawData);
            } catch (LittleStatisticalDataException e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(statisticsData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonStr);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public  @ResponseBody String save(@RequestParam("files[]") List<MultipartFile> files, Model map) throws IOException {
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
        return "file_upload_success";
    }

    @RequestMapping(value = "/Profile", method = RequestMethod.GET)
    public ModelAndView showProfile(){
        ModelAndView modelAndView = new ModelAndView("profile");
        return modelAndView;
    }

    @RequestMapping(value = "/Help", method = RequestMethod.GET)
    public ModelAndView showHelpPage(){
        ModelAndView modelAndView = new ModelAndView("help");
        return modelAndView;
    }

    @RequestMapping(value = "/calculateProfile", method = RequestMethod.POST,consumes = "application/json")
    public ResponseEntity<String> calculateProfileFromFile(@RequestBody String m){
        JSONObject object = new JSONObject(m);
        String jsonStr = "";
        if (fileNames!=null||rawData.size()>0){
            String fileName = "BEM_120.DAT";// todo захардкодил пока
            List<RawData> data = StreamHelper.getRawDataByFileName(fileName,rawData);//rawData.stream().filter(rawData1 -> rawData1.getFileName().equals(fileName)).collect(Collectors.toList());
            ObjectMapper mapper = new ObjectMapper();
            try {
                jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonStr);

    }

    @RequestMapping(value = "/calculateCluglogramme", method = RequestMethod.POST,consumes = "application/json")
    public ResponseEntity<String> calculateCruglogramme(@RequestBody String m){
        JSONObject jsonObject = new JSONObject(m);
        String jsonStr = "";
        String fileName = "BEM_120.DAT";// todo захардкодил пока
        RawData elem = StreamHelper.getRawDataByFileName(fileName,rawData).get(0);
        String jmode = jsonObject.getString("mode");
        if (jmode.equals("grann")){// гранность
            mode = false;
            minBorder=16;
            maxBorder=150;
        }
        else {
            mode=true;// волнистость
            minBorder=0;
            maxBorder=15;
        }
        Converter  converter =new Converter(elem,mode);
        Data data = converter.createData(mode,minBorder,maxBorder,1);//1- номер сечения. Спросить откуда брать сечение
        double[] H=data.getH();
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(H);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonStr);
    }
}
