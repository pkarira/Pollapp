from rest_framework import serializers

from polls.models import Question, Choice

class ChoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model=Choice
        fields=('choice_text','votes')
class QuestionSerializer(serializers.ModelSerializer):
    choice=ChoiceSerializer(many=True)
    class Meta:
        model=Question
        fields=('question_text','id','choice')

