package com.dhxxn.calendary.DTO;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    private User writer;

    private LocalDateTime writeTime;
    private String calendarTime;

    @PrePersist
    public void writeTime() {
        this.writeTime = LocalDateTime.now();
    }
}
