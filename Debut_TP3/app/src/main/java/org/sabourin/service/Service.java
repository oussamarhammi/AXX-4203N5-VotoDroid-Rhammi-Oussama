package org.sabourin.service;

import com.github.mikephil.charting.formatter.IFillFormatter;

import org.sabourin.bd.BD;
import org.sabourin.exceptions.MauvaisVote;
import org.sabourin.exceptions.MauvaiseQuestion;
import org.sabourin.modele.VDQuestion;
import org.sabourin.modele.VDVote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Service {

    private BD maBD;

    public Service(BD maBD){
        this.maBD = maBD;
    }

    public void creerQuestion(VDQuestion vdQuestion) throws MauvaiseQuestion {
        // Validation
        if (vdQuestion.texteQuestion == null || vdQuestion.texteQuestion.trim().length() == 0) throw new MauvaiseQuestion("Question vide");
        if (vdQuestion.texteQuestion.trim().length() < 5) throw new MauvaiseQuestion("Question trop courte");
        if (vdQuestion.texteQuestion.trim().length() > 255) throw new MauvaiseQuestion("Question trop longue");
        if (vdQuestion.idQuestion != null) throw new MauvaiseQuestion("Id non nul. La BD doit le gérer");

        // Doublon du texte de la question
        for (VDQuestion q : toutesLesQuestions()){
            if (q.texteQuestion.toUpperCase().equals(vdQuestion.texteQuestion.toUpperCase())){
                    throw new MauvaiseQuestion("Question existante");
            }
        }

        // Ajout
        vdQuestion.idQuestion = maBD.monDao().insertQuestion(vdQuestion);
    }

    
    public void creerVote(VDVote vdVote) throws MauvaisVote {
        if(vdVote.idVote.equals(vdVote.idQuestion)) throw new MauvaisVote("il existe deja un vote");
        if(vdVote.nom.trim().length() < 4) throw new MauvaisVote("nom trop court");

        for (VDVote q : maBD.monDao().toutLesVotes()){
            if (q.nom.equals(vdVote.nom))
            {
                throw new MauvaisVote("nom deja voté");
            }
        }
        vdVote.idVote = maBD.monDao().insertVote(vdVote);
    }





    public List<VDQuestion> toutesLesQuestions() {
        //TODO Présentement :   retourne une liste vide
        //TODO À faire :        trier la liste reçue en BD par le nombre de votes et la retourner

        Collections.sort(maBD.monDao().touteLesQuestions(), new Comparator<VDQuestion>() {
            @Override
            public int compare(VDQuestion vdq1, VDQuestion vdq2) {

                int voteq1 = maBD.monDao().selectVote(vdq1.idQuestion).size();
                int voteq2 =  maBD.monDao().selectVote(vdq2.idQuestion).size();

                int difference = voteq1 - voteq2;
                if (difference == 0) {

                    // Both are equal
                    return 0;
                }
                else if (difference < 0) {

                    // obj1 < obj2
                    return -1;
                }
                else {

                    // obj1 > obj2
                    return 1;
                }
            }
        });
        return maBD.monDao().touteLesQuestions();
    }

    
    public float moyenneVotes(VDQuestion question) {
        return 0;
    }

    
    public Map<Integer, Integer> distributionVotes(VDQuestion question) {
        return null;
    }
	
	public void supprimerQuestions(){
	}
	
	public void supprimerVotes(){
	}
}
