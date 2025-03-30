package com.example.service.user;

import com.example.base.CRUDService;
import com.example.base.HasValidation;
import com.example.common.exceptions.ValidationException;

import com.example.dataaccess.entity.user.Customer;
import com.example.dataaccess.entity.user.Role;
import com.example.dataaccess.entity.user.User;

import com.example.dataaccess.repository.user.PermissionRepository;
import com.example.dataaccess.repository.user.RoleRepository;
import com.example.dataaccess.repository.user.UserRepository;
import com.example.dataaccess.repository.user.CustomerRepository;
import com.example.dto.LimitedUserDto;
import com.example.dto.LoginDto;

import com.example.util.JwtUtil;
import jakarta.servlet.ServletRequest;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.common.utils.HashUtil;
import com.example.common.exceptions.*;
import com.example.dto.*;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService implements CRUDService<UserDto>, HasValidation<UserDto> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final JwtUtil jwtUtil;
    private final CustomerRepository customerRepository;



    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PermissionRepository permissionRepository,
                       ModelMapper mapper, JwtUtil jwtUtil, CustomerRepository customerRepository, ResourcePatternResolver resourcePatternResolver) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
        this.customerRepository = customerRepository;
    }

    public LimitedUserDto login(LoginDto dto) throws Exception {
        String password = HashUtil.sha1Hash(dto.getPassword());
        User user = userRepository
                .findFirstByUsernameEqualsIgnoreCaseAndPassword(dto.getUsername(), password)
                .orElseThrow(NotFoundException::new);
        if (!user.getEnable()) {
            throw new ValidationException("Your user is disable. contact with support.");
        }
        LimitedUserDto result = mapper.map(user, LimitedUserDto.class);
        result.setToken(jwtUtil.generateToken(result.getUsername()));
        return result;
    }

    @SneakyThrows
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findFirstByUsername(username).orElseThrow(NotFoundException::new);
        return mapper.map(user, UserDto.class);
    }

    @SneakyThrows
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return mapper.map(user, UserDto.class);
    }


    @Override
    public UserDto create(UserDto userDto) throws ValidationException {
        checkValidation(userDto);
        Optional<User> oldUser = userRepository.findFirstByUsername(userDto.getUsername());
        if (oldUser.isPresent()) {
            throw new ValidationException("شما قبلا ثبت نام کرده ابد لطفا وارد شوید.");
        }
        Customer customer = customerRepository.save(mapper.map(userDto.getCustomerDto(), Customer.class));
        User user = mapper.map(userDto, User.class);
        user.setCustomer(customer);
        user.setPassword(HashUtil.sha1Hash(user.getPassword()));
        user.setRegisterDate(LocalDateTime.now());
        user.setEnable(true);
        Optional<Role> userRole = roleRepository.findFirstByNameEqualsIgnoreCase("user");
        if (userRole.isPresent()) {
            HashSet<Role> roles = new HashSet<>();
            roles.add(userRole.get());
            user.setRoles(roles);
        }
        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDto.class);

    }


    @Override
    public void checkValidation(UserDto dto) throws ValidationException {
        if (dto.getCustomerDto() == null) {
            throw new ValidationException("Please enter customer information");
        }
        if (dto.getCustomerDto().getFirstname() == null || dto.getCustomerDto().getFirstname().isEmpty()) {
            throw new ValidationException("Please enter firstname");
        }
        if (dto.getCustomerDto().getLastname() == null || dto.getCustomerDto().getLastname().isEmpty()) {
            throw new ValidationException("Please enter lastname");
        }
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            throw new ValidationException("Please enter username");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new ValidationException("Please enter password");
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new ValidationException("Please enter email");
        }
        if (dto.getMobile() == null || dto.getMobile().isEmpty()) {
            throw new ValidationException("Please enter mobile");
        }
        if (dto.getCustomerDto().getTel() == null || dto.getCustomerDto().getTel().isEmpty()) {
            throw new ValidationException("Please enter tel");
        }
        if (dto.getCustomerDto().getAddress() == null || dto.getCustomerDto().getAddress().isEmpty()) {
            throw new ValidationException("Please enter address");
        }
        if (dto.getCustomerDto().getPostalCode() == null || dto.getCustomerDto().getPostalCode().isEmpty()) {
            throw new ValidationException("Please enter postalCode");
        }
    }


    @Override
    public Page<UserDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return userRepository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x -> mapper.map(x, UserDto.class));
    }


    @Override
    public Boolean delete(Long id) throws NotFoundException {
        userRepository.deleteById(id);
        return true;
    }


    @Override
    public UserDto update(UserDto userDto) throws Exception {
        checkValidation(userDto);
        if (userDto.getId() == null || userDto.getId() <= 0) {
            throw new ValidationException("please enter id to update");
        }
        User user = userRepository.findById(userDto.getId()).orElseThrow(NotFoundException::new);
        user.setUsername(Optional.ofNullable(userDto.getUsername()).orElse(user.getUsername()));
        user.setPassword(Optional.ofNullable(userDto.getPassword()).orElse(user.getPassword()));
        user.setEmail(Optional.ofNullable(userDto.getEmail()).orElse(user.getEmail()));
        user.setMobile(Optional.ofNullable(userDto.getMobile()).orElse(user.getMobile()));
        user.setCustomer(Optional.ofNullable(mapper.map(userDto.getCustomerDto(), Customer.class)).orElse(user.getCustomer()));
        userRepository.save(user);
        return mapper.map(user, UserDto.class);


    }


    public UserDto changePasswordByAdmin(UserDto userDto) throws Exception {

        if (userDto.getId() == null || userDto.getId() <= 0) {
            throw new ValidationException("please enter id to update");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new ValidationException("please enter new password");
        }
        User user = userRepository.findById(userDto.getId()).orElseThrow(NotFoundException::new);
        String password = HashUtil.sha1Hash(userDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return mapper.map(user, UserDto.class);


    }

    public UserDto changePasswordByUser(ChangePassDto dto, UserDto userDto) throws Exception {
        if (dto == null) {
            throw new ValidationException("please fill data");
        }
        User user = userRepository.findById(userDto.getId()).orElseThrow(NotFoundException::new);
        if (!user.getPassword().equals(HashUtil.sha1Hash(dto.getOldPassword()))) {
            throw new ValidationException("Incorrent old password");
        }

        user.setPassword(HashUtil.sha1Hash(dto.getNewPassword()));
        userRepository.save(user);
        return userDto;


    }


    public UserDto updateProfile(UpdateProfileDto dto) throws NotFoundException, ValidationException {

        checkValidation(dto);

        User user = userRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
       user.setMobile(Optional.ofNullable(dto.getMobile()).orElse(user.getMobile()));
        user.setEmail(Optional.ofNullable(dto.getEmail()).orElse(user.getMobile()));
        user.getCustomer().setAddress(Optional.ofNullable(dto.getAddress()).orElse(user.getCustomer().getAddress()));
        user.getCustomer().setTel(Optional.ofNullable(dto.getTel()).orElse(user.getCustomer().getTel()));
        user.getCustomer().setPostalCode(Optional.ofNullable(dto.getPostalCode()).orElse(user.getCustomer().getPostalCode()));
        user.getCustomer().setFirstname(Optional.ofNullable(dto.getFirstname()).orElse(user.getCustomer().getFirstname()));
        user.getCustomer().setLastname(Optional.ofNullable(dto.getLastname()).orElse(user.getCustomer().getLastname()));
        userRepository.save(user);
        return mapper.map(user, UserDto.class);

    }

    private static void checkValidation(UpdateProfileDto dto) throws ValidationException {
        if (dto.getFirstname() == null || dto.getFirstname().isEmpty()){
            throw new ValidationException("please enter your firstname");
        }
        if (dto.getLastname() == null || dto.getLastname().isEmpty()){
            throw new ValidationException("please enter your lastname");
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()){
            throw new ValidationException("please enter your email");
        }
        if (dto.getMobile() == null || dto.getMobile().isEmpty()) {
            throw new ValidationException("Please enter mobile");
        }
        if (dto.getTel() == null || dto.getTel().isEmpty()) {
            throw new ValidationException("Please enter tel");
        }
        if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
            throw new ValidationException("Please enter address");
        }
        if (dto.getPostalCode() == null || dto.getPostalCode().isEmpty()) {
            throw new ValidationException("Please enter postalCode");
        }
    }
}
