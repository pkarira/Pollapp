import json

from django.contrib.auth import logout, login, authenticate
from django.contrib.auth.models import User
from django.http import HttpResponse
from django.shortcuts import render, redirect
from django.utils.decorators import method_decorator
from django.views.decorators.csrf import csrf_exempt
from django.views.generic import View
from rest_framework import status
from rest_framework.authtoken.models import Token
from rest_framework.decorators import authentication_classes, permission_classes, api_view
from rest_framework.renderers import JSONRenderer
from rest_framework.views import APIView

from polls.models import Question, Choice, CreateUser
from polls.serializer import QuestionSerializer, DocumentForm


def index(request):
    return HttpResponse("You're looking at quesstion %s.")


def detail(request, question_id):
    return HttpResponse("You're looking at question %s." % question_id)


@method_decorator(csrf_exempt, name='dispatch')
class TotalResults(APIView):
    def post(self, request):
        outerJson = {}
        results = []
        choices = []
        singleChoice = {}
        singleQuestion = {}
        if request.user.is_authenticated:
            for question in Question.objects.all():
                singleQuestion["question"] = question.question_text
                singleQuestion["id"] = question.id
                for choice in question.choice_set.all():
                    singleChoice["text"] = choice.choice_text
                    singleChoice["votes"] = choice.votes
                    singleChoice["id"] = choice.id
                    choices.append(singleChoice)
                    singleChoice = {}
                singleQuestion["choice"] = choices
                results.append(singleQuestion)
                choices = []
                singleQuestion = {}
            outerJson["results"] = results
            return HttpResponse(json.dumps(outerJson), content_type="application/json")
        else:
            return HttpResponse("wrong user")


@method_decorator(csrf_exempt, name='dispatch')
class Logout(APIView):
    def post(request):
        request.user.auth_token.delete()
        return HttpResponse("You are logged out")


@method_decorator(csrf_exempt, name='dispatch')
class Vote(APIView):
    def post(self, request):
        if request.user.is_authenticated:
            body = json.loads(request.body)
            question_id = int(body["question_id"])
            choice_id = int(body["choice_id"])
            selected_choice = Question.objects.get(pk=question_id).choice_set.get(pk=choice_id)
            selected_choice.votes += 1
            selected_choice.save()
            return HttpResponse("Done")
        else:
            return HttpResponse("Wrong user")


@method_decorator(csrf_exempt, name='dispatch')
class FileUpload(APIView):
    def post(self, request):
        if request.method == 'POST':
            form = DocumentForm(request.POST, request.FILES)
            if form.is_valid():
                form.save()
                return redirect('home')
        else:
            form = DocumentForm()
        return render(request, 'core/model_form_upload.html', {
        'form': form
    })


@method_decorator(csrf_exempt, name='dispatch')
class Login(View):
    def dispatch(self, request, *args, **kwargs):
        return super(Login, self).dispatch(request, *args, **kwargs)

    def post(self, request):
        if (request.method == "POST"):
            body = json.loads(request.body)
            name = body["name"]
            password = body["password"]
            user = authenticate(username=name, password=password)
            outerJson = {}
            if user is not None:
                Token.objects.create(user=user)
                token = Token.objects.get(user=user)
                outerJson["status"] = 1
                outerJson["token"] = token.key
                return HttpResponse(json.dumps(outerJson), content_type="application/json")
            else:
                outerJson["status"] = 0
                outerJson["token"] = "please register"
                return HttpResponse(json.dumps(outerJson), content_type="application/json")


@csrf_exempt
@authentication_classes([])
@permission_classes([])
def addQuestion(request):
    if (request.method == "POST"):
        body = json.loads(request.body)
        question = body["question"]["text"]
        choice1 = body["choice"][0]["text"]
        choice2 = body["choice"][1]["text"]
        from django.utils import timezone
        ques = Question(question_text=question, pub_date=timezone.now())
        ques.save()
        ques.choice_set.create(choice_text=choice1, votes=0)
        ques.choice_set.create(choice_text=choice2, votes=0)
        return HttpResponse("Done")


@method_decorator(csrf_exempt, name='dispatch')
class Register(View):
    def post(self, request):
        if (request.method == "POST"):
            body = json.loads(request.body)
            name = body["name"]
            email = body["email"]
            password = body["password"]
            contact = body["contact"]
            address = body["address"]
            user = authenticate(username=name, password=password)
            if user is not None:
                return HttpResponse("please enter another combination")
            else:
                user = User.objects.create_user(name, email, password)
                user.save()
                CreateUser.objects.create(user=user, contact=contact, address=address)
                return HttpResponse("registered")


@method_decorator(csrf_exempt, name='dispatch')
class GetQuestions(APIView):
    def post(self, request):
        questions = Question.objects.all()
        serializer = QuestionSerializer(questions, many=True)
        json = JSONRenderer().render(serializer.data)
        return HttpResponse(json)
        # outerJson = {}
        # dataJson = {}
        # questionArray = []
        # choices = []
        # singleChoice = {}
        # singleQuestion = {}
        # for question in Question.objects.all():
        #     singleQuestion["question"] = question.question_text
        #     singleQuestion["id"] = question.id
        #     for choice in question.choice_set.all():
        #         singleChoice["text"] = choice.choice_text
        #         singleChoice["id"] = choice.id
        #         choices.append(singleChoice)
        #         singleChoice = {}
        #     singleQuestion["choices"] = choices
        #     questionArray.append(singleQuestion)
        #     choices = []
        #     singleQuestion = {}
        # dataJson["question"] = questionArray
        # outerJson["data"] = dataJson
        # return HttpResponse(json.dumps(outerJson), content_type="application/json")


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
