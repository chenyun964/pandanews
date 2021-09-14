package sg.edu.smu.cs203.pandanews.service;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.Example;
import sg.edu.smu.cs203.pandanews.repository.ExampleRepository;

@Service
public class ExampleServiceImpl implements ExampleService {

    private ExampleRepository examples;

    public ExampleServiceImpl (ExampleRepository examples) {
        this.examples = examples;
    }

    @Override
    public Example getExample(Long id) {
        return examples.findById(id).map(example -> example).orElse(null);
    }
}
