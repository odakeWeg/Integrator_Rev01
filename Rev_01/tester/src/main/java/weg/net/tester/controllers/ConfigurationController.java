package weg.net.tester.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weg.net.tester.utils.EndPointPathUtil;
import weg.net.tester.utils.FilePathUtil;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("")
public class ConfigurationController {
    @GetMapping(EndPointPathUtil.DIRECTOR_FILES_LIST)
    public List<String> getListOfFiles() { 
        try {
            String[] pathnames;
            List<String> list = new ArrayList<String>();
    
            File f = new File(FilePathUtil.TEST_ROUTINE_PATH);
            pathnames = f.list();
    
            for (String pathname : pathnames) {
                System.out.println(pathname);
                list.add(pathname);
            }
            return list;
        }
        catch(Exception e) {
            //@Todo: Non treated exception
            return null;
        }
    }
}
