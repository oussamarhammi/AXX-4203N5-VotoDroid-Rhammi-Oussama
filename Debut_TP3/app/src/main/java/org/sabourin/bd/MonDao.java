package org.sabourin.bd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.sabourin.modele.VDQuestion;
import org.sabourin.modele.VDVote;

import java.util.List;

@Dao
public interface MonDao {
    @Insert
    Long insertQuestion(VDQuestion v);

    //TODO Compléter les autres actions

    @Query("Select * FROM VDQuestion")
    List<VDQuestion> touteLesQuestions();

    @Insert
    Long insertVote(VDVote v);


    @Query("Select idVote FROM VDVote WHERE idQuestion =:idquestion ")
    List<VDVote> insertVote (Long idquestion);




}
