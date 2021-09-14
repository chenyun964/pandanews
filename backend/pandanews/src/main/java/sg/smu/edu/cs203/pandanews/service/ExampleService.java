package sg.smu.edu.cs203.pandanews.service;

import org.springframework.stereotype.Service;

import sg.smu.edu.cs203.pandanews.model.Example;

@Service
public interface ExampleService {
    Example getExample(Long id);
}
