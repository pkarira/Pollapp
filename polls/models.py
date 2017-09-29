# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import datetime

from django.contrib.auth.models import User
from django.utils import timezone
from django.db import models
from django.forms import forms

class Question(models.Model):
    question_text = models.CharField(max_length=200)


class Choice(models.Model):
    question = models.ForeignKey(Question, on_delete=models.CASCADE,related_name='choice')
    choice_text = models.CharField(max_length=200)
    votes = models.IntegerField(default=0)
    def __str__(self):
        return self.question_text

class CreateUser(models.Model):
    user = models.OneToOneField(User, unique=True)
    address=models.CharField(max_length=200)
    contact=models.CharField(max_length=200)

class Document(models.Model):
    description = models.CharField(max_length=255, blank=True)
    document = models.FileField(upload_to='documents/')
    uploaded_at = models.DateTimeField(auto_now_add=True)