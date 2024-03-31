package com.knf.dev.demo.crudapplication.controller;

import com.knf.dev.demo.crudapplication.entity.DataSet;
import com.knf.dev.demo.crudapplication.exception.ResourceNotFoundException;
import com.knf.dev.demo.crudapplication.repository.DataSetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v2/dataset")
public class DataSetController {
    private final DataSetRepository dataRepository;

    public DataSetController(DataSetRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @GetMapping("/all")
    public List<DataSet> getAllData() {
        return dataRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DataSet> getDataById(@PathVariable(value = "id")
                                               Long id) throws ResourceNotFoundException {
        DataSet data = dataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Data not found for this id :: " + id));
        return ResponseEntity.ok().body(data);
    }

    // explicitly define the method findByUserId here


    @GetMapping("/user/{userId}")
    public List<DataSet> getDataByUserId(@PathVariable(value = "userId")
                                         Long userId) {
        if (dataRepository.findAllByUserId(userId).isEmpty()) {
            throw new ResourceNotFoundException
                    ("Data not found for this user id :: " + userId);
        }
        return dataRepository.findAllByUserId(userId).stream().toList();
    }


    @PostMapping("/user/{userId}")
    public String createDataByUserId(@PathVariable(value = "userId")
                                     Long userId, @RequestBody DataSet data) {
        data.setUserId(userId);
        dataRepository.save(data);
        return "Created";
    }

    @PostMapping("/")
    public DataSet createData(@RequestBody DataSet data) {
        return dataRepository.save(data);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<DataSet> updateDataById(@PathVariable(value = "id")
                                                  Long id, @RequestBody DataSet dataDto)
            throws ResourceNotFoundException {

        DataSet data = dataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Data not found for this id :: " + id));

        data.setParam1(dataDto.getParam1());
        data.setParam2(dataDto.getParam2());
        data.setParam3(dataDto.getParam3());
        data.setParam4(dataDto.getParam4());
        data.setParam5(dataDto.getParam5());
        data.setParam6(dataDto.getParam6());
        data.setParam7(dataDto.getParam7());
        data.setParam8(dataDto.getParam8());
        data.setParam9(dataDto.getParam9());
        data.setParam10(dataDto.getParam10());
        data.setParam11(dataDto.getParam11());
        data.setParam12(dataDto.getParam12());
        data.setParam13(dataDto.getParam13());
        data.setParam14(dataDto.getParam14());
        data.setOutput1(dataDto.getOutput1());
        data.setOutput2(dataDto.getOutput2());
        data.setOutput3(dataDto.getOutput3());
        data.setProcessed(dataDto.isProcessed());
        data.setSelectedModel(dataDto.getSelectedModel());
        data.setId(id);
        final DataSet updateData = dataRepository.save(data);
        return ResponseEntity.ok(updateData);
    }

    @DeleteMapping("/{id}")
    public String deleteDataById(@PathVariable(value = "id")
                                 Long id) throws ResourceNotFoundException {
        DataSet data = dataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Data not found for this id :: " + id));
        dataRepository.delete(data);
        return "Deleted";
    }

    @DeleteMapping("/user/{userId}")
    public String deleteDataByUserId(@PathVariable(value = "userId")
                                     Long userId) {
        List<DataSet> data = dataRepository.findAllByUserId(userId);
        if (data.isEmpty()) {
            throw new ResourceNotFoundException
                    ("Data not found for this user id :: " + userId);
        }
        dataRepository.deleteAll(data);
        return "Deleted";
    }
}
