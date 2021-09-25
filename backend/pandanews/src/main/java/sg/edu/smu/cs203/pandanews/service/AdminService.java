package sg.edu.smu.cs203.pandanews.service;

import java.util.List;
import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.Admin;

@Service
public interface AdminService {
    List<Admin> listAdmin();
    Admin getAdmin(Long id);
    Admin addAdmin(Admin admin);
    void deleteAdmin(Long id);
}
