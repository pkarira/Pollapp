
from rest_framework import serializers
from django import forms
from polls.models import Question, Choice, Document


class ChoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model=Choice
        fields=('choice_text','votes')
class QuestionSerializer(serializers.ModelSerializer):
    choice=ChoiceSerializer(many=True)
    class Meta:
        model=Question
        fields=('question_text','id','choice')

class DocumentForm(forms.ModelForm):
    class Meta:
        model = Document
        fields = ('description', 'document', )