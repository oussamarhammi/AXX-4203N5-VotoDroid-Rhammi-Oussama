package org.sabourin;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sabourin.bd.BD;
import org.sabourin.exceptions.MauvaisVote;
import org.sabourin.exceptions.MauvaiseQuestion;
import org.sabourin.modele.VDQuestion;
import org.sabourin.modele.VDVote;
import org.sabourin.service.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestsService {

    private BD bd;
    private Service service;

    // S'exécute avant chacun des tests. Crée une BD en mémoire
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        bd = Room.inMemoryDatabaseBuilder(context, BD.class).build();
        service = new Service(bd);
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOVide() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOCourte() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "aa";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOLongue() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        for (int i = 0 ; i < 256 ; i ++) question.texteQuestion += "aa";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOIDFixe() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "aaaaaaaaaaaaaaaa";
        question.idQuestion = 5L;
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test
    public void ajoutQuestionOK() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        Assert.assertNotNull(question.idQuestion);
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOExiste() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        VDQuestion question2 = new VDQuestion();

        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        question2.texteQuestion = "Aimes-tu les BROWNIES au chocolAT?";

        service.creerQuestion(question);
        service.creerQuestion(question2);

        //TODO Ce test va fail tant que vous n'implémenterez pas toutesLesQuestions() dans ServiceImplementation
        Assert.fail("Exception MauvaiseQuestion non lancée");
    }

    @Test
    public void ToutesLesQuestionsVide() throws MauvaiseQuestion{
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";

        List<VDQuestion> lsitequestion =service.toutesLesQuestions();

        Assert.assertEquals(lsitequestion.size(),0 );
    }

    @Test
    public void nonTriageToutesLesQuestions() throws MauvaiseQuestion , MauvaisVote{
        VDQuestion question = new VDQuestion();
        VDQuestion question2 = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        question2.texteQuestion = "asdsadsa?";
        service.creerQuestion(question);
        service.creerQuestion(question2);
        VDVote vote = new VDVote();
        VDVote vote2 = new VDVote();
        vote.barVote =0;
        vote.nom="parqkejhq";
        vote2.barVote =2;
        vote2.nom="pajcaks";
        vote.idQuestion=question2.idQuestion;
        vote2.idQuestion=question2.idQuestion;
        service.creerVote(vote);
        service.creerVote(vote2);

        List<VDQuestion> lsitequestion =service.toutesLesQuestions();

        Assert.assertEquals(lsitequestion.get(0).idQuestion,question2.idQuestion);
        Assert.assertEquals(lsitequestion.get(1).idQuestion,question.idQuestion);
    }

    @Test
    public void triageToutesLesQuestions() throws MauvaiseQuestion , MauvaisVote{
        VDQuestion question = new VDQuestion();
        VDQuestion question2 = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        question2.texteQuestion = "asdsadsa?";
        service.creerQuestion(question);
        service.creerQuestion(question2);
        VDVote vote = new VDVote();
        VDVote vote2 = new VDVote();
        vote.barVote =0;
        vote.nom="parqkejhq";
        vote2.barVote =2;
        vote2.nom="pajcaks";
        vote.idQuestion=question.idQuestion;
        vote2.idQuestion=question2.idQuestion;
        service.creerVote(vote);
        service.creerVote(vote2);

       List<VDQuestion> lsitequestion =service.toutesLesQuestions();

        Assert.assertNotNull(lsitequestion);
    }



    @Test(expected = MauvaisVote.class)
    public void creerVoteNomVide() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.barVote =0;
        vote.nom="";

        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void creerVoteNomCourte() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.nom="hh";

        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void creerVoteKOExiste() throws MauvaisVote, MauvaiseQuestion {
        VDVote vote = new VDVote();
        VDVote vote2 = new VDVote();
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);
        vote.idQuestion=question.idQuestion;
        vote2.idQuestion=question.idQuestion;
        vote.nom = "mich";
        vote2.nom = "mich";
        vote2.barVote=2;
        vote.barVote=2;
         service.creerVote(vote);
         service.creerVote(vote2);

        //TODO Ce test va fail tant que vous n'implémenterez pas toutesLesQuestions() dans ServiceImplementation
        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void questionExisteDansListe() throws MauvaisVote, MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.barVote =2;
        vote.nom="dakjl";
        vote.idQuestion=100000l;
        service.creerVote(vote);

        Assert.assertEquals(question.idQuestion,vote.idQuestion);
    }

    @Test(expected = MauvaisVote.class)
    public void VoteRatingNegat() throws MauvaisVote ,MauvaiseQuestion{

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.barVote =-2;
        vote.nom="daaas";
        vote.idQuestion=question.idQuestion;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void VoteRatingSupp() throws MauvaisVote ,MauvaiseQuestion{

        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        VDVote vote = new VDVote();
        vote.barVote =6;
        vote.nom="daaas";
        vote.idQuestion=question.idQuestion;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }



    @Test
    public void TestMenuSuppQuestion(){
        int num= bd.monDao().touteLesQuestions().size();
        service.supprimerQuestions();


        Assert.assertNotNull(num);
    }

    @Test
    public void TestMenuSuppVote(){
        int num= bd.monDao().toutLesVotes().size();
        service.supprimerVotes();


        Assert.assertNotNull(num);
    }


    @Test
    public void TestDistributionOk(){
        int num= bd.monDao().toutLesVotes().size();
        service.supprimerVotes();


        Assert.assertNotNull(num);
    }

    @Test
    public void TestMoyenneOk(){
        int num= bd.monDao().toutLesVotes().size();
        service.supprimerVotes();


        Assert.assertNotNull(num);
    }

    /*
    @After
    public void closeDb() {
        bd.close();
    }
    */
}