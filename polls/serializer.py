from django.contrib.auth import get_user_model
from django.contrib.auth.models import User
from rest_framework import serializers
from django import forms
from polls.models import Question, Choice, CreateUser


class ChoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Choice
        fields = ('choice_text', 'votes', 'id')


class BaseUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('username', 'password', 'email')

    def create(self, validated_data):
        user = User.objects.create_user(**validated_data)
        return user


class UserSerializer(serializers.ModelSerializer):
    user = BaseUserSerializer(many=False)

    class Meta:
        model = CreateUser
        fields = ('contact', 'address', 'name', 'password', 'email')

    def create(self, validated_data):
        contact = validated_data.pop('contact')
        address = validated_data.pop('address')
        user = User.objects.create_user(**validated_data)
        newUser = CreateUser.objects.create(user=user, contact=contact, address=address)
        return newUser


class QuestionSerializer(serializers.ModelSerializer):
    choice = ChoiceSerializer(many=True)

    class Meta:
        model = Question
        fields = ('question_text', 'id', 'choice')

    def create(self, validated_data):
        choices = validated_data.pop('choice')
        question = Question.objects.create(**validated_data)
        for choice in choices:
            Choice.objects.create(question=question, choice_text=choice.get('choice_text'), votes=0)
        return question
