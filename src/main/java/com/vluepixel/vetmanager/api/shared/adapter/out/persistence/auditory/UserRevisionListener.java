package com.vluepixel.vetmanager.api.shared.adapter.out.persistence.auditory;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.vluepixel.vetmanager.api.user.core.domain.model.User;

import jakarta.servlet.http.HttpServletRequest;

/**
 * User revision listener.
 */
public class UserRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        UserRevisionEntity userRevisionEntity = (UserRevisionEntity) revisionEntity;

        User user = getUser();

        userRevisionEntity.setUserId(user != null ? user.getId() : null);
        userRevisionEntity.setIp(getIp());
    }

    /**
     * Get the authenticated user.
     *
     * @return the authenticated user.
     */
    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }

        return null;
    }

    /**
     * Get the request.
     *
     * @return the request.
     */
    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            return (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }

        return null;
    }

    /**
     * Get the IP.
     *
     * @return the IP.
     */
    private String getIp() {
        HttpServletRequest request = getRequest();

        if (request != null) {
            return request.getHeader("X-Real-IP");
        }

        return null;
    }
}
