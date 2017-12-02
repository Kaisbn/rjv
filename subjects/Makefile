DOCNAME = Subject
TARGET  = subject.pdf
TEAM    = yaka
THEME   = yaka2019
TREE    = tree.yml

RM         ?= rm -f
BUTLER     ?= ../tools/butler/butler.py
BUTLERFLAGS = --docname="${DOCNAME}" --theme=${THEME} --team=${TEAM} --tree=${TREE}
LATEX       = xelatex
LATEXFLAGS  = -halt-on-error -no-shell-escape

all: ${TARGET}

clean:
	${RM} ${TARGET}
	${RM} *.aux *.log *.out *.toc

.rst.tex:
	${BUTLER} ${BUTLERFLAGS} $< -o $@

.tex.pdf:
	${LATEX} ${LATEXFLAGS} $<
	${LATEX} ${LATEXFLAGS} -jobname=${@:%.pdf=%} $< >/dev/null
	${LATEX} ${LATEXFLAGS} -jobname=${@:%.pdf=%} $< >/dev/null
	${RM} ${@:.pdf=.aux} ${@:.pdf=.log} ${@:.pdf=.out} ${@:.pdf=.toc} \
         ${@:.pdf=.nav} ${@:.pdf=.snm} ${@:.pdf=.vrb}

.SUFFIXES: .rst .tex .pdf

.PHONY: all clean
