package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.ApiConfig;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class ApiLoader {

    public static List<ApiConfig> loadApis() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ApiConfig>>() {}.getType();

        return gson.fromJson(
                new InputStreamReader(
                        ApiLoader.class.getClassLoader().getResourceAsStream("apis.json")
                ),
                listType
        );
    }
}
