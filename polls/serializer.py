from rest_framework import serializers
from django import forms
from polls.models import Question, Choice


class ChoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Choice
        fields = ('choice_text', 'votes', 'id')


class QuestionSerializer(serializers.ModelSerializer):
    choice = ChoiceSerializer(many=True)

    class Meta:
        model = Question
        fields = ('question_text', 'id', 'choice')

    def create(self, validated_data):
        choices = validated_data.pop('choice')
        question = Question.objects.create(**validated_data)
        for choice in choices:
            Choice.objects.create(question=question,choice_text=choice.get('choice_text'),votes=0)
        return question
