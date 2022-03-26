package com.example.appclientserver.controller;

import au.com.bytecode.opencsv.CSVReader;
import com.example.appclientserver.model.ThMccCode;
import com.example.appclientserver.model.TrType;

import com.example.appclientserver.repositories.DataCSVsRepository;
import com.example.appclientserver.service.DataCSVService;
import com.example.appclientserver.service.FileService;
import com.example.appclientserver.model.DataCSV;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/uploadFiles")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {

        Arrays.asList(files)
                .stream()
                .forEach(file -> fileService.uploadFile(file));

        redirectAttributes.addFlashAttribute("message",
                "Вы успешно загрузили все файлы!");

        return "redirect:/";
    }


    // страница с отображением результата
    @GetMapping("/result")
    public String result(Model model) {
        // читаем файл и передаем данные в таблицу
        List<ThMccCode> thMccCodes = null;
        try {
            thMccCodes = new CsvToBeanBuilder(new FileReader("tr_mcc_codes.csv"))
                    .withSeparator(';')
                    .withType(ThMccCode.class)
                    .build()
                    .parse();
            //System.out.println(thMccCodes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("thMccCodes", thMccCodes);

        // читаем файл и передаем данные в таблицу
        List<TrType> trTypes = null;
        try {
            trTypes = new CsvToBeanBuilder(new FileReader("tr_types.csv"))
                    .withSeparator(';')
                    .withType(TrType.class)
                    .build()
                    .parse();
            //System.out.println(trTypes.get(2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("trTypes", trTypes);
        return "result";
    }


    @Autowired
    DataCSVService dataCSVService;

    @Autowired
    private DataCSVsRepository dataCSVsRepository;

    @GetMapping("/save_in_db")
    public String save_in_db(RedirectAttributes redirectAttributes) {
        // Записать данные конкратенирования столбцов
        // сохранение в базу данных
        List<DataCSV> dataCSVs = new ArrayList<>();
        DataCSV dataCSVstr = new DataCSV();
        CSVReader reader_types = null;
        CSVReader reader_mcc_codes = null;
        try {
            reader_types = new CSVReader(new FileReader("tr_types.csv"), ';');
            reader_mcc_codes = new CSVReader(new FileReader("tr_mcc_codes.csv"), ';');
            String[] line;
            String[] line_mcc_codes;
            while (((line = reader_types.readNext()) != null) | (line_mcc_codes = reader_mcc_codes.readNext()) != null){
                if ((line = reader_types.readNext()) == null){
                    //System.out.println("reader_types [id= " + "not" + ", code= " + "not" + "]");
                    //System.out.println("reader_mcc_codes [id= " + line_mcc_codes[0] + ", code= " + line_mcc_codes[1] + "]");
                    dataCSVs.add(new DataCSV("0", line_mcc_codes[0], line_mcc_codes[0]));
                }
                else if ((line_mcc_codes = reader_mcc_codes.readNext()) == null){
                    //System.out.println("reader_types [id= " + line[0] + ", code= " + line[1] + "]");
                    //System.out.println("reader_mcc_codes [id= " + "not" + ", code= " + "not" + "]");
                    dataCSVs.add(new DataCSV(line[0], "0", line[0]));
                }
                else{
                    //System.out.println("reader_types [id= " + line[0] + ", code= " + line[1] + "]");
                    //System.out.println("reader_mcc_codes [id= " + line_mcc_codes[0] + ", code= " + line_mcc_codes[1] + "]");
                    dataCSVs.add(new DataCSV(line[0], line_mcc_codes[0], (line[0] + line_mcc_codes[0])));
                }

            }
            dataCSVsRepository.saveAll(dataCSVs);
            redirectAttributes.addFlashAttribute("message",
                    "Данные успешно сохранились в БД!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/result";
    }

    @GetMapping("export-dataCSVs")
    public void exportCSV(HttpServletResponse response) throws Exception {

        // установить имя файла и тип содержимого
        String filename = "result_csv.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"");

        // создать модуль записи csv
        StatefulBeanToCsv<DataCSV> writer =
                new StatefulBeanToCsvBuilder
                        <DataCSV>(response.getWriter())
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).
                        withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(false).build();

        // записать всех сотрудников в файл csv
        writer.write(dataCSVService.fetchAll());

    }
}
