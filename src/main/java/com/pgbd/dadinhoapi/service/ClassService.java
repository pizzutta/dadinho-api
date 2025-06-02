package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.ClassRegisterDTO;
import com.pgbd.dadinhoapi.dto.ClassUpdateDTO;
import com.pgbd.dadinhoapi.model.Class;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import com.pgbd.dadinhoapi.repository.ClassRepository;
import com.pgbd.dadinhoapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository repository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Class> findById(Long id) {
        return repository.findById(id);
    }

    public List<Class> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Class save(ClassRegisterDTO data) throws ResponseStatusException {
        List<User> students = userRepository.findAllById(data.studentsIds());
        boolean notAllStudents = students.stream().anyMatch(user -> user.getRole() != UserRole.STUDENT);

        if (notAllStudents) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é permitido adicionar alunos na turma");
        }

        Class c = new Class();
        c.setName(data.name());
        c.setGrade(data.grade());
        c.setTeacher(userRepository.getReferenceById(data.teacherId()));
        c.setStudents(students);

        return repository.save(c);
    }

    @Transactional
    public Class save(ClassUpdateDTO data) throws ResponseStatusException {
        Class c = repository.findById(data.id()).orElseThrow(EntityNotFoundException::new);

        List<User> students = userRepository.findAllById(data.studentsIds());
        boolean notAllStudents = students.stream().anyMatch(user -> user.getRole() != UserRole.STUDENT);

        if (notAllStudents) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é permitido adicionar alunos na turma");
        }

        c.setName(data.name());
        c.setGrade(data.grade());
        c.setTeacher(userRepository.getReferenceById(data.teacherId()));
        c.setStudents(students);

        return repository.save(c);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
