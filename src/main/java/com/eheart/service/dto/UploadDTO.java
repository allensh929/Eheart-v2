package com.eheart.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A DTO for the upload file.
 */
public class UploadDTO implements Serializable {

    private Long id;
    private String file;
    private List<String> messages = new ArrayList<>();

    public UploadDTO(Long id, String file) {
        this.id = id;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UploadDTO{" +
            "id=" + id +
            ", file='" + file + "'" +
            '}';
    }
}
