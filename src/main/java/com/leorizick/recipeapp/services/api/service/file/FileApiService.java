package com.leorizick.recipeapp.services.api.service.file;

import com.leorizick.recipeapp.entities.account.Account;
import com.leorizick.recipeapp.entities.file.AccountImg;
import com.leorizick.recipeapp.entities.file.RecipeImg;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.file.FileManagement;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileApiService {

    private final AuthenticationContext authenticationContext;

    private final FileManagement fileManagement;
    private final RecipeCrud recipeCrud;

    private final String recipePath = "assets/images/recipes/";
    private final String accountPath = "assets/images/account/";
    private final String categoryPath = "assets/images/categories/";

    private final ResourceLoader resourceLoader;

    @Transactional
    public void uploadRecipeImage(MultipartFile multipartFile, Long recipeId) {
        Recipe recipe = recipeCrud.findById(recipeId);
        var name = nameFile(multipartFile);
        var filePath = createDirectory(recipePath, recipeId, name);

        try {
            Files.copy(multipartFile.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        RecipeImg recipeImg = RecipeImg.builder()
                .recipe(recipe)
                .path(filePath)
                .name(name)
                .cover(false)
                .build();

        fileManagement.saveRecipeImg(recipeImg);
    }

    @Transactional
    public void uploadRecipeCoverImage(MultipartFile multipartFile, Long recipeId) {
        Recipe recipe = recipeCrud.findById(recipeId);
        var name = nameFile(multipartFile);
        var filePath = createDirectory(recipePath, recipeId, name);
        var oldCover = fileManagement.getRecipeCover(recipeId);

        if (oldCover != null) {
            try {
                Files.deleteIfExists(Path.of(oldCover.getPath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {

            Files.copy(multipartFile.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        RecipeImg recipeImg = RecipeImg.builder()
                .recipe(recipe)
                .path(filePath)
                .name(name)
                .cover(true)
                .build();

        fileManagement.saveRecipeImg(recipeImg);
    }


    public Resource downloadRecipeResource(Long recipeId, String name) {
        return resourceLoader.getResource("file:" + recipePath + recipeId + "/" + name);

    }

    public Resource downloadAccountResource(Long accountId, String name) {
        return resourceLoader.getResource("file:" + accountPath + accountId + "/" + name);

    }

    public List<Resource> downloadAllRecipeImages(Long recipeId, List<String> names) {
        List<Resource> list = new ArrayList<>();
               names.forEach(name -> list.add(downloadRecipeResource(recipeId, name)));

        return list;

    }

    public Resource downloadCategoryResource(Long categoryId) {
        return resourceLoader.getResource("file:" + categoryPath + categoryId + ".png");

    }

    public String searchRecipeCoverImgByRecipeId(Long recipeId) {
        var cover = fileManagement.getRecipeCover(recipeId);
        if (cover == null) {
            return null;
        }
        return cover.getName();
    }

    public List<String> searchAllRecipeImages(Long recipeId) {
        var list = fileManagement.getRecipeImages(recipeId);
        if (list == null) {
            return null;
        }
        return list.stream().map(img -> img.getName()).collect(Collectors.toList());
    }

    @Transactional
    public void uploadAccountImage(MultipartFile multipartFile) {
        Account account = authenticationContext.getAccount();
        var name = nameFile(multipartFile);
        var filePath = createDirectory(accountPath, account.getId(), name);

        clearDirectoryImgs(accountPath + account.getId());
        try {

            Files.copy(multipartFile.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Upload failed!" + e.getMessage());
        }

        AccountImg accountImg = AccountImg.builder()
                .account(account)
                .path(filePath)
                .name(name)
                .build();

        fileManagement.saveAccountImg(accountImg);

    }

    private String getOriginalFileExt(String name) {
        int ext = name.lastIndexOf(".");
        return name.substring(ext + 1);
    }

    private String createDirectory(String path, Long id, String name) {
        new File(path + id).mkdir();
        return path + id + "/" + name;
    }

    private String nameFile(MultipartFile multipartFile) {
        return UUID.randomUUID() + "." + getOriginalFileExt(multipartFile.getOriginalFilename());
    }

    private void clearDirectoryImgs(String path) {
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();
            for (File file : files) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
