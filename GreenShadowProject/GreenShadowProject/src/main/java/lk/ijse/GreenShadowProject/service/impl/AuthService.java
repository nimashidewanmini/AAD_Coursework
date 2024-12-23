package lk.ijse.GreenShadowProject.service.impl;


import lk.ijse.GreenShadowProject.dto.ReqRespDTO;
import lk.ijse.GreenShadowProject.dto.UserDTO2;
import lk.ijse.GreenShadowProject.entity.User;
import lk.ijse.GreenShadowProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository ourUserRepo;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRespDTO signUp(ReqRespDTO registrationRequest){
        ReqRespDTO resp = new ReqRespDTO();
        try {
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            User ourUserResult = ourUserRepo.save(user);
            if (ourUserResult != null && ourUserResult.getId()>0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRespDTO signIn(ReqRespDTO signinRequest){
        ReqRespDTO response = new ReqRespDTO();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtUtil.generateToken(user);
            var refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRespDTO refreshToken(ReqRespDTO refreshTokenReqiest){
        ReqRespDTO response = new ReqRespDTO();
        String ourEmail = jwtUtil.extractUsername(refreshTokenReqiest.getToken());
        User users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtil.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtUtil.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }



    public ReqRespDTO getAllUsers() {
        ReqRespDTO response = new ReqRespDTO();
        try {
            List<User> usersList = ourUserRepo.findAll();
            if (usersList.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No Users Found");
            } else {
                List<UserDTO2> userDTOList = usersList.stream()
                        .map(user -> new UserDTO2(user.getId(), user.getEmail(), user.getPassword()))
                        .collect(Collectors.toList());

                response.setStatusCode(200);
                response.setUsersList(userDTOList);
                response.setMessage("Users Fetched Successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("Error fetching users: " + e.getMessage());
        }
        return response;
    }


    public void deleteUser(Integer Code) {
        ourUserRepo.deleteById(Code);
    }


}
