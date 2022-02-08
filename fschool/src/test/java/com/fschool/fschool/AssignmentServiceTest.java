package com.fschool.fschool;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.*;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Services.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssignmentServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private CommentRepository commentRepository;

    @Autowired
    @InjectMocks
    private AssignmentService assignmentService;
    
}
