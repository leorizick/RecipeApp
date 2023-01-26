package com.leorizick.recipeapp.web.file;

import com.leorizick.recipeapp.services.api.service.file.FileApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileApiService fileApiService;

    @PostMapping(value = "/api/recipe/{recipeId}/uploadImage")
    public ResponseEntity<String> uploadRecipeImage (@RequestParam("file")MultipartFile multipartFile, @PathVariable Long recipeId){
        fileApiService.uploadRecipeImage(multipartFile, recipeId);
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded!");
    }

    @PostMapping(value = "/api/recipe/{recipeId}/cover/uploadImage")
    public ResponseEntity<String> uploadRecipeCoverImage (@RequestParam("file")MultipartFile multipartFile, @PathVariable Long recipeId){
        fileApiService.uploadRecipeCoverImage(multipartFile, recipeId);
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded!");
    }

    @GetMapping(value = "/api/recipe/cover/downloadImage/{recipeId}/{fileName}", produces = "image/jpeg; charset=UTF-8")
    public ResponseEntity<InputStreamResource> downloadRecipeImage (@PathVariable Long recipeId, @PathVariable String fileName) throws IOException {
        var resource = fileApiService.downloadRecipeResource(recipeId, fileName);

        if(!resource.exists()){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @GetMapping(value = "/api/recipe/cover/{recipeId}", produces = "image/jpeg; charset=UTF-8")
    public ResponseEntity<InputStreamResource> downloadRecipeCoverImage (@PathVariable Long recipeId) throws IOException {
        var resource = fileApiService.downloadRecipeCover(recipeId);

        if(!resource.exists()){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @GetMapping(value = "/api/account/downloadImage/{accountId}/{fileName}", produces = "image/jpeg; charset=UTF-8")
    public ResponseEntity<InputStreamResource> downloadAccountImage (@PathVariable Long accountId, @PathVariable String fileName) throws IOException {
        var resource = fileApiService.downloadAccountResource(accountId,fileName);

        if(!resource.exists()){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @GetMapping(value = "/api/category/downloadImage/{categoryId}", produces = "image/jpeg; charset=UTF-8")
    public ResponseEntity<InputStreamResource> downloadCategoryImage (@PathVariable Long categoryId)throws IOException {
        var resource = fileApiService.downloadCategoryResource(categoryId);

        if(!resource.exists()){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @PostMapping(value = "/api/account/uploadImage")
    public ResponseEntity<String> uploadUserImage (@RequestParam("file")MultipartFile multipartFile){
        fileApiService.uploadAccountImage(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded!");
    }

    @DeleteMapping(value = "/api/recipe/{id}/deleteImage/{name}")
    public ResponseEntity<String> deleteRecipeImage (@PathVariable Long id, @PathVariable String name){
        fileApiService.deleteRecipeImage(id, name);
        return ResponseEntity.status(HttpStatus.OK).body("File deleted!");
    }
}
