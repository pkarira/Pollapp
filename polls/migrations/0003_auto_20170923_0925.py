# -*- coding: utf-8 -*-
# Generated by Django 1.11.4 on 2017-09-23 09:25
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('polls', '0002_createuser'),
    ]

    operations = [
        migrations.AlterField(
            model_name='createuser',
            name='user',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL, unique=True),
        ),
    ]