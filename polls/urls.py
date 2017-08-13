from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^$', views.index),
    url(r'^(?P<question_id>[0-9]+)/$', views.detail),
    url(r'^results/$', views.totalResults),
    url(r'^(?P<question_id>[0-9]+)/singleresults/$', views.singleResult),
    url(r'^questions$', views.getQuestions),
    url(r'^vote$', views.vote),
    url(r'^add$', views.addQuestion),
]
