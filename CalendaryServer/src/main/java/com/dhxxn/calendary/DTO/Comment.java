package com.dhxxn.calendary.DTO;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User writer;

    @ManyToOne(cascade = CascadeType.ALL)
    private Diary board;

    private LocalDateTime writeCm;

    @PrePersist
    public void writeCm() {
        this.writeCm = LocalDateTime.now();
    }
}
