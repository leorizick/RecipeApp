package com.leorizick.recipeapp.services.domain.service.account;

import com.leorizick.recipeapp.entities.account.Credential;
import com.leorizick.recipeapp.repositories.account.CredentialRepository;
import com.leorizick.recipeapp.services.exceptions.AlreadyDeletedException;
import com.leorizick.recipeapp.services.exceptions.EmailAlreadyInUseException;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class CredentialCrud {
    private static final String NOT_FOUND_MESSAGE = "Credential %s not found";

    private final CredentialRepository credentialRepository;

    public Credential save(Credential credential) {
        var emailEnabled = credentialRepository.findByEmailAndIsEnabledTrue(credential.getEmail());
        if (emailEnabled.isPresent()) {
            throw new EmailAlreadyInUseException();
        }
        return credentialRepository.save(credential);
    }

    public Credential findById(Long id) {
        return credentialRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
    }

    public Page<Credential> findAll(Pageable pageable) {
        return credentialRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Long id) {
        Credential credential = findById(id);

        if (!credential.isEnabled())
            throw new AlreadyDeletedException(Credential.class, id.toString());

        credential.setEnabled(false);
        save(credential);
    }

}
