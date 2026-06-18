package com.x.contact.service;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 14:39
 */
public interface ContactService {

    void addContact(Long userId, Long contactId);

    void deleteContact(Long userId, Long contactId);

    void getContactList(Long userId);
}
