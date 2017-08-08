from time import timezone

from django.http import HttpResponse

from polls.models import Question, Choice


def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")


def detail(request, question_id):
    return HttpResponse("You're looking at question %s." % question_id)


def results(request, question_id):
    response = "You're looking at the results of question %s."
    return HttpResponse(response % question_id)


def vote(request, question_id):
    return HttpResponse("You're voting on question %s." % question_id)


def addQuestion(question, choice1, choice2):
    q = Question(question_text="What's new?", pub_date=timezone.now())
    q.save()
    q.choice_set.create(choice_text='Not much', votes=0)
