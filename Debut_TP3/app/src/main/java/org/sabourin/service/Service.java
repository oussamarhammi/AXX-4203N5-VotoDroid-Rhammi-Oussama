package org.sabourin.service;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.formatter.IFillFormatter;

import org.sabourin.R;
import org.sabourin.bd.BD;
import org.sabourin.databinding.CreationQuestionBinding;
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

    public int editText;


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
        if(vdVote.nom.trim().length() < 4) throw new MauvaisVote("nom trop court");
        if(vdVote.barVote<0 || vdVote.barVote>5) throw new MauvaisVote("vote entre 0 et 5");
        boolean ab= false;
        for (VDQuestion a: toutesLesQuestions()) {
            if (a.idQuestion.equals(vdVote.idQuestion)) {
                ab=true;
            }

        } if(ab==false) throw new MauvaisVote("question N'existe pas");

        for (VDVote q : maBD.monDao().toutLesVotes()){
            if (q.nom.equals(vdVote.nom) && q.idQuestion.equals(vdVote.idQuestion))
                throw new MauvaisVote("ce nom a deja voté à cette question");
            }
        vdVote.idVote = maBD.monDao().insertVote(vdVote);

        }



    public List<VDQuestion> toutesLesQuestions() {
        //TODO Présentement :   retourne une liste vide
        //TODO À faire :        trier la liste reçue en BD par le nombre de votes et la retourner

        List<VDQuestion> lsitequestion =maBD.monDao().touteLesQuestions();
        Collections.sort(lsitequestion, new Comparator<VDQuestion>() {
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
                    return 1;
                }
                else {

                    // obj1 > obj2
                    return -1;
                }
            }
        });
        return lsitequestion;
    }

    
    public float moyenneVotes(VDQuestion question) {
       int vote =maBD.monDao().selectVote(question.idQuestion).size();


        return 0;
    }

    
    public Map<Integer, Integer> distributionVotes(VDQuestion question) {

        return null;
    }
	
	public void supprimerQuestions(){

        maBD.monDao().supprimerQuestion();
	}
	
	public void supprimerVotes(){
        maBD.monDao().supprimerVote();
	}
}
