package org.example.bigdataproject1;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller

public class PlotController {

    @Value("classpath:plot.R")
    private Resource rSource;
    @Autowired
    private DataRepository dataRepository;
    private int currentIndex = 0;
    private  List<DataPoint> allData;
    @Autowired
    private Function<List<Double>, String> plotFunction;
    @RequestMapping (value = "/plot", produces = "image/svg+xml")
    public ResponseEntity<String> plot() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Refresh", "1");
        String currentSvg = "";
        try{
            if(allData == null || allData.isEmpty()){
                allData = dataRepository.findAll();
                System.out.println(allData.size());
            }
            if (allData.isEmpty()) {
                return new ResponseEntity<>("No data found", headers, HttpStatus.OK);
            }
            int end = Math.min(currentIndex + 1, allData.size());
            List<Double> values = allData.subList(0,end).stream()
                    .map(DataPoint::getValue).collect(Collectors.toList());

            synchronized (plotFunction) {
                currentSvg = plotFunction.apply(values);
            }

            currentIndex = (end == allData.size()) ? 0 : end;
            return new ResponseEntity<>(currentSvg, headers, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error"+e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }


}