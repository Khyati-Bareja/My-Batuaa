package com.myBatuaa.service;

import com.myBatuaa.exception.UnauthorizedAccessException;
import com.myBatuaa.exception.UserAlreadyRegisteredException;
import com.myBatuaa.model.Role;
import com.myBatuaa.model.User;
import com.myBatuaa.model.Wallet;
import com.myBatuaa.repository.UserRepository;
import com.myBatuaa.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  WalletService walletService;
    
    @Autowired
    private WalletRepository walletrepository;
    
   // @Autowired
   // private Wallet wallet;
    
    // to register for  new user
    @Override
    public User registerUser(User user) {
        String normalizedEmail = user.getEmailId().trim().toLowerCase();
        user.setEmailId(normalizedEmail);
        // Check if email already exists
        if (userRepository.existsByEmailIdIgnoreCase(normalizedEmail)) {

            throw new UserAlreadyRegisteredException("User already registered");
        }

            // 1. Save user first
        User savedUser = userRepository.save(user);
        // 2. Generate wallet
        String walletId = walletService.generateWalletId(savedUser.getEmailId(), savedUser.getMobileNumber());
        // create wallet
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setBalance(BigDecimal.valueOf(0.0));
        wallet.setUser(savedUser);
        wallet.setCreatedAt(LocalDateTime.now());

        // 3. Save wallet
        walletrepository.save(wallet);

        return savedUser;
    }

    @Override
    public User login(String emailId, String password, Role role) {
        String normalizedEmail = emailId.toLowerCase();

        return userRepository.findByEmailId(normalizedEmail)
                .filter(user -> user.getPassword().equals(password))
                .filter(user -> user.getRole() == role)

                .orElseThrow(() -> new UnauthorizedAccessException("Invalid email, password, or role"));
    }
}
