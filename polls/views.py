from time import timezone

from django.http import HttpResponse

from polls.models import Question, Choice


def index(request):
    s = ""
    for question in Question.objects.all():
        s += question.question_text + " "
        for choice in question.choice_set.all():
            s += choice.choice_text + " "
        s += "\n"
    return HttpResponse(s)


def detail(request, question_id):
    return HttpResponse("You're looking at question %s." % question_id)


def results(request, question_id):
    response = "You're looking at the results of question %s."
    return HttpResponse(response % question_id)


def vote(request, question_id):
    return HttpResponse("You're voting on question %s." % question_id)


def addQuestion(question, choice1, choice2):
    ques = Question(question_text=question, pub_date=timezone.now())
    ques.save()
    ques.choice_set.create(choice_text=choice1, votes=0)
    ques.choice_set.create(choice_text=choice2, votes=0)


def getQuestions(request):
    s = ""
    for question in Question.objects.all():
        s += question.question_text + " "
        for choice in question.choice_set.all:
            s += choice.choice_text + " "
        s += "\n"
    return HttpResponse(s)
