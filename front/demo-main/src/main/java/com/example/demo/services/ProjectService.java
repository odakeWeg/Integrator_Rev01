package com.example.demo.services;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.demo.models.ConfigurationModel;
import com.example.demo.models.MappingModel;
import com.example.demo.models.ProjectModel;
import com.example.demo.models.RoutineModel;
import com.example.demo.utils.DownloadPathUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectService {
    public boolean downloadProject(ProjectModel project) {
        try {
            downloadMapping(project.getMappings());
            downloadRoutine(project.getRoutines());
            downloadConfig(project.getConfigurations());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void downloadMapping(List<MappingModel> mappingList) throws Exception{
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = Obj.writeValueAsString(mappingList);
        try (PrintWriter out = new PrintWriter(new FileWriter(DownloadPathUtil.MAPPING_PATH+"mapping.json"))) {
            out.write("{\"mapping\":" + jsonStr + "}");
        } catch (Exception e) {
            throw new Exception("Mapping download error!");
        }
    }

    private void downloadRoutine(List<RoutineModel> routineList) throws Exception {
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr;
        for (int i = 0; i < routineList.size(); i++) {
            jsonStr = Obj.writeValueAsString(routineList.get(i).getTag());
            downloadIndividualTag(jsonStr, routineList.get(i).getNome());
        }
    }

    private void downloadIndividualTag(String jsonStr, String routineName) throws Exception {
        try (PrintWriter out = new PrintWriter(new FileWriter(DownloadPathUtil.ROUTINE_PATH+routineName+".json"))) {
            out.write("{\"list\":" + jsonStr + "}");
        } catch (Exception e) {
            throw new Exception("Routine download error!");
        }
    }

    private void downloadConfig(List<ConfigurationModel> configurationList) {
        //@Todo
    }
}
