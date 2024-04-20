package com.tweeter.User.Service.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@AllArgsConstructor
@Component
public class RSAKeys {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAKeys() throws Exception {
        if (keysExist()) {
            // Load the existing keys
            KeyPair pair = KeyGeneratorUtil.loadKeyPair();
            this.publicKey = (RSAPublicKey) pair.getPublic();
            this.privateKey = (RSAPrivateKey) pair.getPrivate();
        } else {
            // Generate new keys and save them
            KeyPair pair = KeyGeneratorUtil.generateRsaKey();
            KeyGeneratorUtil.saveKeyPair(pair);
            this.publicKey = (RSAPublicKey) pair.getPublic();
            this.privateKey = (RSAPrivateKey) pair.getPrivate();
        }
    }

    private boolean keysExist() {
        return Files.exists(Paths.get("rsa_public.key")) && Files.exists(Paths.get("rsa_private.key"));
    }
}

