package ru.ssau.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public  @ResponseBody
    String save(@RequestParam("files[]") List<MultipartFile> files, Model map) throws IOException {
        List<String> fileNames = new ArrayList<String>();
//        dataList = new LinkedList<>();//тут данные уже прошедшие через парсер
        if(null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
                InputStream is = multipartFile.getInputStream();
                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
//                ProfileStatistics statistics = DatReader.readData(fileName,is);
//                dataList.add(statistics);
            }
        }
        return "file_upload_success";
    }
}
