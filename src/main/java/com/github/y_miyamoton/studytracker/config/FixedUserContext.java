package com.github.y_miyamoton.studytracker.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
class FixedUserContext implements UserContext {

    @Override
    public Long currentUserId() {
        return 1L;
    }
}