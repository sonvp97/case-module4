package com.codegym.model.service.Impl;

import com.codegym.model.dto.RoleDto;
import com.codegym.model.dto.UserDto;
import com.codegym.model.entity.User;
import com.codegym.model.repository.UserRepository;
import com.codegym.model.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Value("${file-upload}")
    private String fileUpload;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<UserDto> findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return Optional.of(modelMapper.map(user,UserDto.class));
    }

    @Override
    public void save(UserDto userDto) {
        MultipartFile multipartFile = userDto.getAvatar();
        String filename = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(userDto.getAvatar().getBytes(), new File(fileUpload + filename));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        User user = modelMapper.map(userDto,User.class);
        if(!userDto.getPassword().isEmpty()){
            String hashedPassword = BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt(10));
            user.setPassword(hashedPassword);
        }
        user.setAvatar(filename);
        userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public Iterable<UserDto> findAllByRole(RoleDto roleDto) {
        Iterable<User> entities = userRepository.findAllByRole(roleDto);
        return StreamSupport.stream(entities.spliterator(),true)
                .map(entiti -> modelMapper.map(entiti,UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<UserDto> findAll() {
        Iterable<User> entities = userRepository.findAll();
        return StreamSupport.stream(entities.spliterator(),true).map(entiti -> modelMapper.map(entiti, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
//        List<User> entities = userRepository.findAll();
//        List<UserDto> dtos = new ArrayList<>(entities.stream()
//                .parallel()
//                .map(entiti -> modelMapper.map(entiti,UserDto.class))
//                .collect(Collectors.toList()));

//        return new PageImpl<>(dtos);
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findAllByFullNameContaining(String fullName, Pageable pageable) {
//        Page<User> entities = userRepository.findAllByFullNameContaining(fullName,pageable);
////        List<UserDto> dtos = new ArrayList<>(entities.stream()
////                .parallel()
////                .map(entity -> modelMapper.map(entity,UserDto.class))
////                .collect(Collectors.toList()));
        return userRepository.findAllByFullNameContaining(fullName,pageable);
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null
        || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())){
            return false;
        }
        return authentication.isAuthenticated();
    }
}
