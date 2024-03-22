package com.example.apilearn.ok;

import com.example.apilearn.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

    @Value("${openweathermap.api.key}") // Внедрение ключа API из файла свойств
    private String apiKey;

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    @GetMapping("/")
    public String showWeather(Model model) {
        String cityName = "Talgar"; // Название города, для которого вы хотите получить информацию о погоде

        String url = String.format(API_URL, cityName, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        // Извлекаем описание погоды
        String weatherDescription = response.getWeather()[0].getDescription();
        // Извлекаем температуру
        int temperature = response.getMain().getTemp() - 273;

        // Добавляем атрибуты в модель
        model.addAttribute("cityName", cityName);
        model.addAttribute("weatherDescription", weatherDescription);
        model.addAttribute("temperature", temperature); // Добавляем температуру в модель

        return "home";
    }


}