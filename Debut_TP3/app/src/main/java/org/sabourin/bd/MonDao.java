package org.sabourin.bd;

import androidx.room.Dao;
import androidx.room.Delete;
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




    @Query("Select * FROM VDVote WHERE idQuestion =:idquestion ")
    List<VDVote> selectVote(Long idquestion);


    @Query("Select * FROM VDVote")
    List<VDVote> toutLesVotes();

    @Insert
    Long insertVote(VDVote v);

    @Query("DELETE FROM VDQuestion")
    void supprimerQuestion();

    @Query("DELETE FROM VDVote")
    void supprimerVote();

}
