package lk.ijse.GreenShadowProject.controller;


import lk.ijse.GreenShadowProject.service.impl.AuthService;
import lk.ijse.GreenShadowProject.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/manage")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.PATCH, RequestMethod.OPTIONS})
public class UserManageController {

    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(UserManageController.class);

    @GetMapping
    public ResponseUtil getAllUsers() {
        logger.info("getAllUsers successfully");
        return new ResponseUtil(200, "OK", authService.getAllUsers());
    }

    @DeleteMapping
    public  ResponseUtil deleteUsers(@RequestParam("uCode") Integer uCode){
        logger.info("deleteUsers successfully");
        authService.deleteUser(uCode);
        return new ResponseUtil(200,"Deleted",null);

    }
}
