import plotly.graph_objects as go
from plotly.subplots import make_subplots

# Sample data
x1 = [0, 1, 2, 3, 4]
y1 = [10, 11, 12, 13, 14]
x2 = [8, 9, 10, 11, 12]
y2 = [10, 11, 12, 13, 14]

y1 = [ 0 ,0 ,0 ,1 ,1 ,26 ,98 ,247 ,497 ,794 ,954 ,1103 ,1098 ,990 ,822 ,683 ,514 ,408 ,323 ,210 ,163 ,115 ,84 ,75 ,54 ,54 ,50 ,40 ,41 ,37 ,33 ,45 ,45 ,43 ,27 ,39 ,30 ,16 ,18 ,22 ,13 ,10 ,12 ,10 ,9 ,13 ,5 ,6 ,7 ,3 ,7 ,2 ,5 ,4 ,3 ,4 ,3 ,3 ,6 ,8 ,4 ,2 ,2 ,5 ,3 ,5 ,5 ,1 ,2 ,3 ,2 ,0 ,1 ,3 ,0 ,2 ,3 ,2 ,3 ,0 ,0 ,1 ,1 ,0 ,2 ,0 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,0 ,2 ,0 ,0 ,1 ,0 ]
x1 = [ 150000 ,152000 ,154000 ,156000 ,158000 ,160000 ,162000 ,164000 ,166000 ,168000 ,170000 ,172000 ,174000 ,176000 ,178000 ,180000 ,182000 ,184000 ,186000 ,188000 ,190000 ,192000 ,194000 ,196000 ,198000 ,200000 ,202000 ,204000 ,206000 ,208000 ,210000 ,212000 ,214000 ,216000 ,218000 ,220000 ,222000 ,224000 ,226000 ,228000 ,230000 ,232000 ,234000 ,236000 ,238000 ,240000 ,242000 ,244000 ,246000 ,248000 ,250000 ,252000 ,254000 ,256000 ,258000 ,260000 ,262000 ,264000 ,266000 ,268000 ,270000 ,272000 ,274000 ,276000 ,278000 ,280000 ,282000 ,284000 ,286000 ,288000 ,290000 ,292000 ,294000 ,296000 ,298000 ,300000 ,302000 ,304000 ,306000 ,308000 ,310000 ,312000 ,314000 ,316000 ,318000 ,320000 ,322000 ,324000 ,326000 ,328000 ,330000 ,332000 ,334000 ,336000 ,338000 ,340000 ,342000 ,344000 ,346000 ,348000]
y2 = [0, 0, 0, 0, 1, 0, 1, 3, 10, 30, 47, 118, 233, 364, 588, 669, 764, 790, 704, 584, 489, 328, 249, 172, 111, 78, 85, 83, 103, 108, 152, 158, 179, 160, 164, 167, 156, 131, 118, 103, 75, 67, 71, 57, 72, 64, 71, 84, 88, 83, 93, 78, 74, 54, 48, 33, 21, 25, 27, 20, 26, 22, 23, 28, 28, 32, 27, 33, 23, 23, 25, 17, 22, 15, 16, 13, 15, 8, 17, 14, 5, 11, 6, 5, 5, 14, 7, 6, 7, 6, 0, 5, 3, 0, 3, 4, 2, 4, 0, 7, 1]
x2 = [3574000, 3576000, 3578000, 3580000, 3582000, 3584000, 3586000, 3588000, 3590000, 3592000, 3594000, 3596000, 3598000, 3600000, 3602000, 3604000, 3606000, 3608000, 3610000, 3612000, 3614000, 3616000, 3618000, 3620000, 3622000, 3624000, 3626000, 3628000, 3630000, 3632000, 3634000, 3636000, 3638000, 3640000, 3642000, 3644000, 3646000, 3648000, 3650000, 3652000, 3654000, 3656000, 3658000, 3660000, 3662000, 3664000, 3666000, 3668000, 3670000, 3672000, 3674000, 3676000, 3678000, 3680000, 3682000, 3684000, 3686000, 3688000, 3690000, 3692000, 3694000, 3696000, 3698000, 3700000, 3702000, 3704000, 3706000, 3708000, 3710000, 3712000, 3714000, 3716000, 3718000, 3720000, 3722000, 3724000, 3726000, 3728000, 3730000, 3732000, 3734000, 3736000, 3738000, 3740000, 3742000, 3744000, 3746000, 3748000, 3750000, 3752000, 3754000, 3756000, 3758000, 3760000, 3762000, 3764000, 3766000, 3768000, 3770000, 3772000, 3774000]

ticktext = []
for i in range(0, len(x1)):
    if i % 10 == 0:
        ticktext.append(str(x1[i]))
    else:
        ticktext.append('')

for i in range(0, len(x2)):
    if i % 10 == 0:
        ticktext.append(str(x2[i]))
    else:
        ticktext.append('')

# Create subplots with a single row and 2 columns
fig = make_subplots(
    rows=1, cols=2,  # 1 row, 2 columns
    shared_yaxes=True,  # Share the y-axis to keep the scale consistent
    column_widths=[0.45, 0.45],  # Adjust the width of each subplot
    horizontal_spacing=0.05  # Set spacing between subplots
)

# Add the first plot to the first subplot (x1, y1)
fig.add_trace(go.Scatter(x=x1, y=y1, mode='lines', name='Part 1'),
              row=1, col=1)

# Add the second plot to the second subplot (x2, y2)
fig.add_trace(go.Scatter(x=x2, y=y2, mode='lines', name='Part 2'),
              row=1, col=2)

# Adjust the X-axis ranges to simulate the "gap"
fig.update_xaxes(range=[min(x1), max(x1)], row=1, col=1)  # Set range for the first plot
fig.update_xaxes(range=[min(x2), max(x2)], row=1, col=2)  # Set range for the second plot

# Customize the layout to make the plots closer
fig.update_layout(
    title="Multiple Plots with Gap on X-axis",
    showlegend=True,
    xaxis_title="X-axis",
    yaxis_title="Y-axis",
    height=500,  # Adjust the height of the entire figure
    width=800,  # Adjust the width of the entire figure
    margin=dict(l=40, r=40, t=40, b=40)  # Reduce margins for closer plots
)

# Show the plot
fig.show()

### Create traces
#trace1 = go.Scatter(x=x1, y=y1, mode='lines', name='Part 1')
#trace2 = go.Scatter(x=x2, y=y2, mode='lines', name='Part 2')
#
## Create layout with axis break (visual simulation)
## make the x axis 
#layout = go.Layout(
#    xaxis=dict(
#        range=[x1[0], x2[-1]],
#        tickvals=x1 + x2,#[0, 1, 2, 3, 4, 8, 9, 10, 11, 12],  # Skipping the range between 4 and 8
#        title='ASD',
#        ticktext=ticktext,
#    ),
#    yaxis=dict(title='Ejecuciones'),
#    title="Plot QuickSort Execution Time",
#)
#
## Create the figure
##fig = go.Figure(data=[trace1], layout=layout)
#fig = go.Figure(data=[trace1, trace2], layout=layout)
#fig.show()
