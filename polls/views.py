import json

from django.contrib.auth import logout, login, authenticate
from django.contrib.auth.models import User
from django.http import HttpResponse
from django.shortcuts import render, redirect
from django.utils.decorators import method_decorator
from django.views.decorators.csrf import csrf_exempt
from django.views.generic import View
from rest_framework import status, generics
from rest_framework.authtoken.models import Token
from rest_framework.permissions import AllowAny, IsAdminUser
from rest_framework.views import APIView

from polls.models import Question, Choice, CreateUser
from polls.serializer import QuestionSerializer, UserSerializer, BaseUserSerializer


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
class Login(APIView):
    permission_classes = []
    authentication_classes = []
    def dispatch(self, request, *args, **kwargs):
        return super(Login, self).dispatch(request, *args, **kwargs)

    def post(self, request):
        if (request.method == "POST"):
            body = json.loads(request.body)
            name = body["username"]
            password = body["password"]
            email = body["email"]
            user = authenticate(username=name, password=password,email=email)
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


@method_decorator(csrf_exempt, name='dispatch')
class AddQuestion(generics.CreateAPIView):
    serializer_class = QuestionSerializer


@method_decorator(csrf_exempt, name='dispatch')
class Register(generics.CreateAPIView):
    permission_classes = []
    authentication_classes = []
    serializer_class = BaseUserSerializer


@method_decorator(csrf_exempt, name='dispatch')
class GetQuestions(generics.ListAPIView):
    serializer_class = QuestionSerializer
    queryset = Question.objects.all()


def changeChoiceOption(request, choice_id, text):
    choice = Choice.objects.get(pk=choice_id)
    choice.choice_text = text
    choice.save()


def changeQuestion(request, question_id, text):
    question = Question.objects.get(pk=question_id)
    question.question_text = text
    question.save()


@method_decorator(csrf_exempt, name='dispatch')
class SingleResults(generics.ListAPIView):
    serializer_class = QuestionSerializer

    def get_queryset(self):
        queryset = Question.objects.filter(pk=self.kwargs['question_id'])
        return queryset
