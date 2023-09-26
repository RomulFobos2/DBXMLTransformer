package com.tander.taskOneApp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "test_tbl")
public class Field {
    @Id
    private int field;

    public Field(int field) {
        this.field = field;
    }
}
