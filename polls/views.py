from time import timezone
import json
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt

from polls.models import Question, Choice


def index(request):
    return HttpResponse("You're looking at question %s.")


def detail(request, question_id):
    return HttpResponse("You're looking at question %s." % question_id)


def totalResults(request):
    outerJson = {}
    results = []
    choices = []
    singleChoice = {}
    singleQuestion = {}
    for question in Question.objects.all():
        singleQuestion["questions"] = question.question_text
        singleQuestion["id"] = question.id
        for choice in question.choice_set.all():
            singleChoice["text"] = choice.choice_text
            singleChoice["votes"] = choice.votes
            singleChoice["id"] = choice.id
            choices.append(singleChoice)
            singleChoice = {}
        singleQuestion["choices"] = choices
        results.append(singleQuestion)
        choices = []
        singleQuestion = {}
    outerJson["results"] = results
    return HttpResponse(json.dumps(outerJson), content_type="application/json")


@csrf_exempt
def vote(request):
    if (request.method == "POST"):
        body = json.loads(request.body)
        question_id = int(body["question_id"])
        choice_id = int(body["choice_id"])
        selected_choice = Question.objects.get(pk=question_id).choice_set.get(pk=choice_id)
        selected_choice.votes += 1
        selected_choice.save()


def addQuestion(request, question, choice1, choice2):
    ques = Question(question_text=question, pub_date=timezone.now())
    ques.save()
    ques.choice_set.create(choice_text=choice1, votes=0)
    ques.choice_set.create(choice_text=choice2, votes=0)


def getQuestions(request):
    outerJson = {}
    dataJson = {}
    questionArray = []
    choices = []
    singleChoice = {}
    singleQuestion = {}
    for question in Question.objects.all():
        singleQuestion["questions"] = question.question_text
        singleQuestion["id"] = question.id
        for choice in question.choice_set.all():
            singleChoice["text"] = choice.choice_text
            choices.append(singleChoice)
            singleChoice = {}
        singleQuestion["choices"] = choices
        questionArray.append(singleQuestion)
        choices = []
        singleQuestion = {}
    dataJson["question"] = questionArray
    outerJson["data"] = dataJson
    return HttpResponse(json.dumps(outerJson), content_type="application/json")


def changeChoiceOption(request, choice_id, text):
    choice = Choice.objects.get(pk=choice_id)
    choice.choice_text = text
    choice.save()


def changeQuestion(request, question_id, text):
    question = Question.objects.get(pk=question_id)
    question.question_text = text
    question.save()


def singleResult(request, question_id):
    outerJson = {}
    results = []
    choices = []
    singleChoice = {}
    singleQuestion = {}
    for question in Question.objects.filter(pk=question_id):
        singleQuestion["questions"] = question.question_text
        singleQuestion["id"] = question.id
        for choice in question.choice_set.all():
            singleChoice["text"] = choice.choice_text
            singleChoice["votes"] = choice.votes
            singleChoice["id"] = choice.id
            choices.append(singleChoice)
            singleChoice = {}
        singleQuestion["choices"] = choices
        results.append(singleQuestion)
        choices = []
        singleQuestion = {}
    outerJson["results"] = results
    return HttpResponse(json.dumps(outerJson), content_type="application/json")
