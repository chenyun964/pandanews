package sg.edu.smu.cs203.pandanews.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import sg.edu.smu.cs203.pandanews.model.Admin;
import sg.edu.smu.cs203.pandanews.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository admins;

    @Autowired
	private PasswordEncoder bcryptEncoder;

    public AdminServiceImpl (AdminRepository admins) {
        this.admins = admins;
    }

    @Override
    public Admin getAdmin(Long id) {
        return admins.findById(id).orElse(null);
    }

    @Override
    public List<Admin> listAdmin(){
        return admins.findAll();
    }

    @Override
    public Admin addAdmin(Admin admin){
        Admin newAdmin = new Admin();
		newAdmin.setUsername(admin.getUsername());
		newAdmin.setPassword(bcryptEncoder.encode(admin.getPassword()));
        return admins.save(newAdmin);
    }

    public Admin updateAdmin(Long id, Admin newAdmin){
        return admins.findById(id).map(admin -> {
            return admins.save(admin);
        }).orElse(null);
    }

    @Override
    public void deleteAdmin(Long id){
        admins.deleteById(id);
    }
}
