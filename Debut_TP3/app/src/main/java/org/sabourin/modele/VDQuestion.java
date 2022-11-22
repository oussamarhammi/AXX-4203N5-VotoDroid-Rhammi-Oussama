package org.sabourin.modele;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity
public class VDQuestion  {
    @PrimaryKey(autoGenerate = true)
    public Long idQuestion;

    public String texteQuestion;
}
