from django.conf.urls import url

from . import views
urlpatterns = [
    url(r'^$', views.index),
    url(r'^(?P<question_id>[0-9]+)/$', views.detail),
    url(r'^results$', views.TotalResults.as_view()),
    url(r'^(?P<question_id>[0-9]+)/singleresults/$', views.singleResult),
    url(r'^questions$', views.GetQuestions.as_view()),
    url(r'^vote$', views.Vote.as_view()),
    url(r'^add$', views.addQuestion),
    url(r'^register$', views.Register.as_view()),
    url(r'^login', views.Login.as_view()),
    url(r'^logout', views.logout),
]
