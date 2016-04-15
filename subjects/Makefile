DOCNAME = Sujet
TARGET  = main.pdf

RM         ?= rm -f
MPP         = /sgoinfre/qualite/mpp/src/mpp.py
MPPFLAGS    =
PANDOC      = pandoc
PANDOCFLAGS = --latex-engine=xelatex --listings --toc --no-tex-ligatures --normalize
BUTLER      = /sgoinfre/qualite/butler/butler.py
BUTLERFLAGS = --docname=${DOCNAME} \
        --project=../project.yml --tree=tree.yml
LATEX       = xelatex
LATEXFLAGS  = -halt-on-error -no-shell-escape

DEPS   := ../project.xml $(shell ${MPP} --list-includes ${TARGET:.pdf=.md})

all: ${TARGET}

assistant: MPPFLAGS += --assistant

assistant: all

clean:
	${RM} ${TARGET}

${TARGET}:

.md.mppmd:
	${MPP} ${MPPFLAGS} $< > $@

#.mppmd.pdf:
#	${PANDOC} ${PANDOCFLAGS} -f markdown -t latex -o $@ $<

.mppmd.maintex:
	${PANDOC} ${PANDOCFLAGS} -t latex -o $@ $<

.maintex.tex:
	${BUTLER} ${BUTLERFLAGS} $< > $@

.tex.pdf:
	${LATEX} ${LATEXFLAGS} -jobname=${@:%.pdf=%} $<
	${LATEX} ${LATEXFLAGS} -jobname=${@:%.pdf=%} $< >/dev/null
	${RM} ${@:.pdf=.aux} ${@:.pdf=.log} ${@:.pdf=.out} ${@:.pdf=.toc}

.SUFFIXES: .md .mppmd .maintex .tex .pdf
#EOF
