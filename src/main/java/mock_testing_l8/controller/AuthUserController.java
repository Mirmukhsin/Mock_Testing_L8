package mock_testing_l8.controller;

import jakarta.validation.Valid;
import mock_testing_l8.dto.AuthUserCreateDto;
import mock_testing_l8.dto.AuthUserGetDto;
import mock_testing_l8.dto.AuthUserUpdateDto;
import mock_testing_l8.service.AuthUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authUser")
public class AuthUserController {
    private final AuthUserService service;

    public AuthUserController(AuthUserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody AuthUserCreateDto dto) {
        service.create(dto);
        return new ResponseEntity<>("Successfully Created - User", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody AuthUserUpdateDto dto) {
        service.update(dto);
        return new ResponseEntity<>("Successfully Updated - User", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("Successfully Deleted - User", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthUserGetDto> get(@PathVariable Integer id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthUserGetDto>> list() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
