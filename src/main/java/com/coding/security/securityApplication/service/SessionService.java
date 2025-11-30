package com.coding.security.securityApplication.service;


import com.coding.security.securityApplication.entity.Session;
import com.coding.security.securityApplication.entity.User;
import com.coding.security.securityApplication.repositroy.sessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final sessionRepository sessionRepository;

    private final int SESSION_LIMIT=2;

    public void generateNewSession(User user, String refreshToken) {

        List<Session> userSessions = sessionRepository.findByUser(user);

        if(userSessions.size()==SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session lastRecentlyUsedSession= userSessions.get(0);

            // if it has session equal to limit we delete the lastRecentlyUsed session fromDB
            sessionRepository.delete(lastRecentlyUsedSession);
        }
        //else create the sesiion and save it.
        Session newSession= Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);

    }


    // if we have a refresh token in the repo means this is a valid session
    void validateSession(String refreshToken){
       Session session= sessionRepository.findByRefreshToken(refreshToken).orElseThrow(()->
             new SessionAuthenticationException("Session with refresh token not found:" + refreshToken));
        session.setLastUsedAt(LocalDateTime.now());

        sessionRepository.save(session);
    }

}
