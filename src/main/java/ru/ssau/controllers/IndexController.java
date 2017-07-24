package ru.ssau.controllers;

import model.Auxiliary.ReaderRawData;
import model.Calculation.ConsiderStatistics;
import model.Data.RawData;
import model.Data.StatisticsData;
import model.Exception.FileFormatException;
import model.Exception.LittleStatisticalDataException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private ArrayList<RawData> rawData;

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
    public  @ResponseBody
    String save(@RequestParam("files[]") List<MultipartFile> files, Model map) throws IOException {
        List<String> fileNames = new ArrayList<String>();
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
}
